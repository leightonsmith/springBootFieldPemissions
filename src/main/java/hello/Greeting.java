package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

public class Greeting implements Restricted {

    private final long id;
    private final String content;
    private final String hidden;
    @JsonIgnore
    private final String ignored;

    public String getIgnored() {
        return ignored;
    }

    public Greeting(long id, String content, String hidden) {
        this.id = id;
        this.content = content;
        this.hidden = hidden;
        this.ignored = "some value that shouldn't appear";
    }

    public String getHidden() {
        return hidden;
    }

    public long getId() {
        return id;
    }

    @JsonView(View.ReadOnly.class)
    public String getContent() {
        return content;
    }

    @Override
    public boolean isRestricted() {
        return id > 0;
    }
}
