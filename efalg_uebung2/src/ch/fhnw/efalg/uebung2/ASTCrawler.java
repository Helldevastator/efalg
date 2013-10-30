package ch.fhnw.efalg.uebung2;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

public class ASTCrawler extends TreePathScanner<Object, Void>{

	@Override
    public Object visitClass(ClassTree classTree, Void nothing) {
        System.out.println("classvisited!");
        
        System.out.println();
        return super.visitClass(classTree, nothing);
    }
	
    @Override
    public Object visitMethod(MethodTree methodTree, Void nothing) {
    	System.out.println("methodvisited!");
    	TreePath path = super.getCurrentPath();
    	
    
        return super.visitMethod(methodTree, nothing);
    }
}
