// SPDX-FileCopyrightText: Copyright 2025 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0
package org.firebirdsql.asciidoctor.sinceuntil;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinceMacroProcessorTest {

    @Test
    void sinceRendering() {
        String input = "Jaybird specific property (since:[Jaybird 6]).";
        String expected = """
                <div class="paragraph">
                <p>Jaybird specific property (<em class="since"><strong>Since:</strong> Jaybird 6</em>).</p>
                </div>""";

        try (Asciidoctor asciidoctor = Asciidoctor.Factory.create()) {
            asciidoctor.unregisterAllExtensions();
            asciidoctor.createGroup()
                    .inlineMacro(SinceMacroProcessor.class)
                    .register();

            String html = asciidoctor.convert(input, createOptions()).trim();
            assertEquals(expected, html);
        }
    }

    @Test
    void shouldAutomaticallyRegister() {
        String input = "Jaybird specific property (since:[Jaybird 6]).";
        String expected = """
                <div class="paragraph">
                <p>Jaybird specific property (<em class="since"><strong>Since:</strong> Jaybird 6</em>).</p>
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