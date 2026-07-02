// SPDX-FileCopyrightText: Copyright 2026 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0
package org.firebirdsql.asciidoctor.issue;

import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.PhraseNode;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.Format;
import org.asciidoctor.extension.FormatType;
import org.asciidoctor.extension.InlineMacroProcessor;
import org.asciidoctor.extension.Name;

import java.net.URI;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * GitHub issue link macro.
 * <p>
 * Converts {@code issue:[123]} to {@code https://github.com/{fb-issue-repo}/issues/123[#123]}, where
 * {@code fb-issue-repo} is an attribute of the form {@code organization/project} (syntax is <em>not</em> validated).
 * If the {@code fb-issue-repo} is not set, this macro will simply produce {@code 123}. The target (i.e. value
 * {@code 123}) must be numeric).
 * </p>
 * <p>
 * If {@code fb-issue-repo} is set to an absolute URL, the macro will render {@code {fb-issue-repo}/issues/123[#123]}.
 * </p>
 *
 * @since 1.1
 */
@Name("issue")
@Format(FormatType.SHORT)
public class IssueMacroProcessor extends InlineMacroProcessor {

    private static final URI GITHUB_BASE_URI = URI.create("https://github.com/");
    private static final String ISSUE_REPO_ATTRIBUTE = "fb-issue-repo";
    private static final Pattern ALL_DIGITS = Pattern.compile("\\d+");

    @Override
    public PhraseNode process(StructuralNode parent, String target, Map<String, Object> attributes) {
        Document document = parent.getDocument();
        if (!document.hasAttribute(ISSUE_REPO_ATTRIBUTE) || !ALL_DIGITS.matcher(target).matches()) {
            return createPhraseNode(parent, "quoted", target);
        }

        String issueUrl = GITHUB_BASE_URI
                .resolve(document.getAttribute(ISSUE_REPO_ATTRIBUTE) + "/issues/")
                .resolve(target)
                .toString();
        return createPhraseNode(parent, "anchor", '#' + target, attributes,
                Map.of("type", ":link", "target", issueUrl));
    }
}
