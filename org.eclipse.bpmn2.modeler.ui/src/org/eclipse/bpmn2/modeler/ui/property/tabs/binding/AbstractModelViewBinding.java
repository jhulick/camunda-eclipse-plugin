package org.eclipse.bpmn2.modeler.ui.property.tabs.binding;

import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

/**
 * Feature agnostic model view binding
 * 
 * @author nico.rehwaldt
 *
 * @param <T> the control type to bind
 * @param <V> the model value type
 */
public abstract class AbstractModelViewBinding<T, V> {

	/**
	 * The model
	 */
	protected EObject model;
	
	/**
	 * The view
	 */
	protected T control;
	
	public AbstractModelViewBinding(EObject model, T control) {
		this.model = model;
		this.control = control;
	}

	/**
	 * Updates the view value with the given data
	 * 
	 * @param value
	 */
	public abstract void setViewValue(V value);
	
	/**
	 * Returns the current view value
	 * 
	 * @return
	 */
	public abstract V getViewValue();

	/**
	 * Returns the model value
	 * @return
	 */
	public abstract V getModelValue();
	
	/**
	 * Sets the model value
	 * 
	 * @param value
	 */
	public abstract void setModelValue(V value);
	
	/**
	 * Returns the editing domain for the managed model
	 * 
	 * @param model
	 * @return
	 */
	protected TransactionalEditingDomain getTransactionalEditingDomain() {
		return TransactionUtil.getEditingDomain(model);
	}
	
	/**
	 * Establishes a bi-directional binding between model and view
	 * 
	 */
	public void establish() {
		// update view value with latest value from model
		setViewValue(getModelValue());
		
		establishModelViewBinding();
		establishViewModelBinding();
	}

	/**
	 * Establish the model view binding.
	 * Can be overridden by subclasses. 
	 */
	protected void establishModelViewBinding() {
		
	}
	
	/**
	 * Establish the view model binding.
	 * Can be overridden by subclasses. 
	 */
	protected void establishViewModelBinding() {
		
	}
}
