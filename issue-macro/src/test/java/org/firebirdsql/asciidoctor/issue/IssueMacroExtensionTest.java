// SPDX-FileCopyrightText: Copyright 2026 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0
package org.firebirdsql.asciidoctor.issue;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class IssueMacroExtensionTest {

    private Asciidoctor asciidoctor;

    @BeforeEach
    void setUp() {
        asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.unregisterAllExtensions();
        asciidoctor.createGroup()
                .inlineMacro(IssueMacroProcessor.class)
                .register();
    }

    @AfterEach
    void tearDown() {
        asciidoctor.close();
    }

    @Test
    void issueLinkRendering_happyPath() {
        assertIssueLinkRendering("FirebirdSQL/jaybird", "940",
                "<p>See <a href=\"https://github.com/FirebirdSQL/jaybird/issues/940\">#940</a>.</p>");
    }

    @Test
    void issueLinkRendering_noRepoConfigured() {
        assertIssueLinkRendering(null, "940", "<p>See 940.</p>");
    }

    @Test
    void issueLinkRendering_notANumber() {
        assertIssueLinkRendering("FirebirdSQL/jaybird", "not a number", "<p>See not a number.</p>");
    }

    @Test
    void issueLinkRendering_repoIsAbsoluteUrl() {
        assertIssueLinkRendering("https://example.org/test", "940",
                "<p>See <a href=\"https://example.org/test/issues/940\">#940</a>.</p>");
    }

    void assertIssueLinkRendering(String fbIssueRepo, String target, String expectedFragment) {
        String input = """
                = Title
                %s
                
                See issue:[%s].
                """.formatted(fbIssueRepo != null ? ":fb-issue-repo: " + fbIssueRepo : "", target);
        String html = asciidoctor.convert(input, createOptions());

        assertThat(html, containsString(expectedFragment));
    }

    private static Options createOptions() {
        return Options.builder()
                .backend("html5")
                .docType("book")
                .safe(SafeMode.SERVER)
                .build();
    }

}