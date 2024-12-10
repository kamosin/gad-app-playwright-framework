package api.models.flashpost;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FlashpostSettings(String hexColor) {
}
