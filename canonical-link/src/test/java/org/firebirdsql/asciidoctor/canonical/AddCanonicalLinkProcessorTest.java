package org.firebirdsql.asciidoctor.canonical;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

class AddCanonicalLinkProcessorTest {

    private Asciidoctor asciidoctor;

    @BeforeEach
    void setUp() {
        asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.unregisterAllExtensions();
        asciidoctor.createGroup()
                .docinfoProcessor(AddCanonicalLinkProcessor.class)
                .register();
    }

    @AfterEach
    void tearDown() {
        asciidoctor.close();
    }

    @Test
    void canonicalLinkRendering() {
        String input = """
                = Title
                :fb-canonical-html: https://example.org/canonical.html
                
                Body
                """;
        String expectedFragment = "<link rel=\"canonical\" href=\"https://example.org/canonical.html\"/>";

        String html = asciidoctor.convert(input, createOptions());

        assertThat(html, containsString(expectedFragment));
    }

    @Test
    void noCanonicalLinkRendering_ifAttributeNotSet() {
        String input = """
                = Title
                
                Body
                """;

        String html = asciidoctor.convert(input, createOptions());

        assertThat(html, not(containsString("<link rel=\"canonical\"")));
    }

    @Test
    void shouldAutomaticallyRegister() {
        String input = """
                = Title
                :fb-canonical-html: https://example.org/canonical.html
                
                Body
                """;
        String expectedFragment = "<link rel=\"canonical\" href=\"https://example.org/canonical.html\"/>";

        try (Asciidoctor asciidoctor = Asciidoctor.Factory.create()) {
            String html = asciidoctor.convert(input, createOptions());

            assertThat(html, containsString(expectedFragment));
        }
    }

    private static Options createOptions() {
        return Options.builder()
                .backend("html5")
                .docType("book")
                // Mark standalone so it contains the output of the docinfo processor
                .standalone(true)
                .safe(SafeMode.SERVER)
                .build();
    }


}