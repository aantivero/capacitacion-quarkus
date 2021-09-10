package ar.gov.agip;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;

public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Generate some book objects with random data
        from("timer:generateBooks?period={{timer.period}}&delay={{timer.delay}}")
                .log("Generating randomized books CSV data")
                .process("bookGenerator")
                // Marshal each book to CSV format
                .marshal().bindy(BindyType.Csv, Book.class)
                // Write CSV data to file
                .to("file:{{csv.location}}");

        // Consume book CSV files
        from("file:{{csv.location}}?delay=1000")
                .log("Reading books CSV data from ${header.CamelFileName}")
                .unmarshal().bindy(BindyType.Csv, Book.class)
                .split(body())
                .to("direct:aggregateBooks");

        // Aggregate books based on their genre
        from("direct:aggregateBooks")
                .setHeader("BookGenre", simple("${body.genre}"))
                .aggregate(simple("${body.genre}"), new GroupedBodyAggregationStrategy()).completionInterval(5000)
                .log("Processed ${header.CamelAggregatedSize} books for genre '${header.BookGenre}'")
                .to("seda:processed");

        from("seda:processed")
                // Marshal books back to CSV format
                .marshal().bindy(BindyType.Csv, Book.class)
                .setHeader(Exchange.FILE_NAME, simple("books-${header.BookGenre}-${exchangeId}.csv"))
                // Send aggregated book genre CSV files to an FTP host
                .to("sftp://{{ftp.username}}@{{ftp.host}}:{{ftp.port}}/uploads/books?password={{ftp.password}}")
                .log("Uploaded ${header.CamelFileName}");
    }
}
