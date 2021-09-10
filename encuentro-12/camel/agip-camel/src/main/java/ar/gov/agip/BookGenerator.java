package ar.gov.agip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Processor to generate {@Book}s with a random set of data.
 */
@ApplicationScoped
@Named
public class BookGenerator implements Processor {

    private static final String[] BOOK_GENRES = { "Action", "Crime", "Horror" };

    private static final String[] BOOK_DESCRIPTION = {
            "Awesome",
            "Amazing",
            "Fantastic",
            "Incredible",
            "Tremendous",
    };

    private static final String[] FIRST_NAMES = {
            "Fyodor",
            "Jane",
            "Leo",
            "Oscar",
            "William",
    };

    private static final String[] LAST_NAMES = {
            "Austen",
            "Dostoevsky",
            "Shakespeare",
            "Tolstoy",
            "Wilde",
    };

    @Override
    public void process(Exchange exchange) throws Exception {
        Random random = new Random();
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String genre = BOOK_GENRES[random.nextInt(BOOK_GENRES.length)];
            String description = BOOK_DESCRIPTION[random.nextInt(BOOK_DESCRIPTION.length)];
            String title = String.format("The %s book of %s #%d", description, genre, i);

            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String author = String.format("%s %s", firstName, lastName);

            Book book = new Book();
            book.setId(i);
            book.setAuthor(author);
            book.setTitle(title);
            book.setGenre(genre);
            books.add(book);
        }

        exchange.getMessage().setBody(books);
    }
}
