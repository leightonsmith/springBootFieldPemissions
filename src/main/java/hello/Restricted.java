package hello;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Created by lsmith on 12/06/17.
 */
@JsonFilter("restrict filter")
public interface Restricted {
    boolean isRestricted();
}
