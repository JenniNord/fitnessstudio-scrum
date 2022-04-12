/**
 */
package sp.model.sp;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Backlog</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sp.model.sp.Backlog#getWorkitems <em>Workitems</em>}</li>
 * </ul>
 *
 * @see sp.model.sp.SPPackage#getBacklog()
 * @model
 * @generated
 */
public interface Backlog extends EObject {
	/**
	 * Returns the value of the '<em><b>Workitems</b></em>' containment reference list.
	 * The list contents are of type {@link sp.model.sp.WorkItem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workitems</em>' containment reference list.
	 * @see sp.model.sp.SPPackage#getBacklog_Workitems()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<WorkItem> getWorkitems();

} // Backlog
