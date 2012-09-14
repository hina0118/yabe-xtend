package play.xtend;

import com.google.common.base.Predicate;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtend.core.XtendStandaloneSetup;
import org.eclipse.xtend.core.compiler.batch.XtendBatchCompiler;
import org.eclipse.xtext.mwe.NameBasedFilter;
import org.eclipse.xtext.mwe.PathTraverser;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.validation.Issue;
import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.vfs.VirtualFile;

import java.io.File;

public class XtendPlugin extends PlayPlugin {
	private PlayXtendCompiler compiler;

	public XtendPlugin() {
		Injector injector = new XtendStandaloneSetup().createInjectorAndDoEMFRegistration();
		injector = injector.createChildInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(PlayXtendCompiler.class).toInstance(new PlayXtendCompiler());
			}
		});
		compiler = injector.getInstance(PlayXtendCompiler.class);

		String outputPath = Play.getFile("app").getAbsolutePath();
		String sourcePath = Play.getFile("xtend").getAbsolutePath();

		compiler.setUseCurrentClassLoaderAsParent(true);

		compiler.setOutputPath(outputPath);
		compiler.setSourcePath(sourcePath);
	}

	@Override
	public void onLoad() {
		compile();
	}

	@Override
	public void beforeDetectingChanges() {
		compile();
	}
	
	private void compile() {
		if (!compiler.compile()) {
			throw new XtendCompileException(compiler.issueMessages);
		}
	}

	public static class PlayXtendCompiler extends XtendBatchCompiler {
		private static final String separator = "<br>" + System.getProperty("line.separator");
		private String issueMessages;

		@Override
		public boolean compile() {
			issueMessages = null;
			return super.compile();
		}

		@Override
		protected void reportIssues(Iterable<Issue> issues) {
			StringBuilder message = new StringBuilder();
			for (Issue issue : issues) {
				message.append(createIssueMessage(issue).toString());
				message.append(separator);
			}
			issueMessages = message.toString();
		}
		
		private StringBuilder createIssueMessage(Issue issue) {
			StringBuilder issueBuilder = new StringBuilder(separator);
			issueBuilder.append(issue.getSeverity()).append(": \t");
			URI uriToProblem = issue.getUriToProblem();
			if (uriToProblem != null) {
				URI resourceUri = uriToProblem.trimFragment();
				issueBuilder.append(resourceUri.lastSegment()).append(" - ");
				if (resourceUri.isFile()) {
					issueBuilder.append(resourceUri.toFileString());
				}
			}
			issueBuilder.append(separator).append(issue.getLineNumber()).append(": ").append(issue.getMessage());
			return issueBuilder;
		}
	}

	public static class XtendCompileException extends RuntimeException {
		public XtendCompileException() {
			super();
		}
		
		public XtendCompileException(String message) {
			super(message);
		}
		
		public XtendCompileException(String message, Throwable cause) {
			super(message, cause);
		}

		public XtendCompileException(Throwable cause) {
			super(cause);
		}
	}
}