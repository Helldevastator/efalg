package ch.fhnw.efalg.uebung2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

public class Source {

	/**
	 * Sources:
	 * https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#invoking-the-compiler-from-code-the-java-compiler-api
	 * http://stackoverflow.com/questions/1928157/how-to-get-surrounding-method-in-java-source-file-for-a-given-line-number
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		List<File> files = new ArrayList<>();
		files.add(new File("TestClass1.java"));
		
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		
		Iterable<? extends CompilationUnitTree> parseResult = null;
		SourcePositions sourcePositions = null;
		
		try {
			JavacTask javacTask = (JavacTask) task;
			sourcePositions = Trees.instance(javacTask).getSourcePositions();
    		parseResult = javacTask.parse();
    	} catch (IOException e) {

    		// Parsing failed
    		e.printStackTrace();
    		System.exit(0);
    	}
		
    	for (CompilationUnitTree compilationUnitTree : parseResult) {
    		ASTCrawler c = new ASTCrawler();
    		TreePath p = new TreePath(compilationUnitTree);
    		c.scan(p, null);
    	}
		
		
	}

}
