package hello;

public class Salutation {

    private final long id;
    private final String content;
    private final Greeting greeting;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Greeting getGreeting() {
        return greeting;
    }

    public Salutation(long id, String content) {
        this.id = id;
        this.content = content;
        this.greeting = new Greeting(id, content, "some hidden content");

    }
}
