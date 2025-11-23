package org.firebirdsql.asciidoctor.sinceuntil;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UntilMacroProcessorTest {

    @Test
    void untilRendering() {
        String input = "Jaybird specific property (until:[Jaybird 6]).";
        String expected = """
                <div class="paragraph">
                <p>Jaybird specific property (<em class="until"><strong>Removed in:</strong> Jaybird 6</em>).</p>
                </div>""";

        try (Asciidoctor asciidoctor = Asciidoctor.Factory.create()) {
            asciidoctor.unregisterAllExtensions();
            asciidoctor.createGroup()
                    .inlineMacro(UntilMacroProcessor.class)
                    .register();

            Options options = createOptions();
            String html = asciidoctor.convert(input, options).trim();
            assertEquals(expected, html);
        }
    }

    @Test
    void shouldAutomaticallyRegister() {
        String input = "Jaybird specific property (until:[Jaybird 6]).";
        String expected = """
                <div class="paragraph">
                <p>Jaybird specific property (<em class="until"><strong>Removed in:</strong> Jaybird 6</em>).</p>
                </div>""";

        try (Asciidoctor asciidoctor = Asciidoctor.Factory.create()) {
            String html = asciidoctor.convert(input, createOptions()).trim();
            assertEquals(expected, html);
        }
    }

    private static Options createOptions() {
        return Options.builder()
                .backend("html5")
                .docType("article")
                .safe(SafeMode.SERVER)
                .build();
    }

}