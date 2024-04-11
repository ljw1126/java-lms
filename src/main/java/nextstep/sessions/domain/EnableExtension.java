package nextstep.sessions.domain;

import java.util.Arrays;

public enum EnableExtension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String text;

    EnableExtension(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static EnableExtension from(String other) {
        return Arrays.stream(values())
                .filter(enableExtension -> enableExtension.text.equalsIgnoreCase(other))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 확장자 입니다"));
    }

}
