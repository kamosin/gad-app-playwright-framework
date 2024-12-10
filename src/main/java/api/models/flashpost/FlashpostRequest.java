package api.models.flashpost;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FlashpostRequest (String body, FlashpostSettings settings, Boolean is_public) {
}
