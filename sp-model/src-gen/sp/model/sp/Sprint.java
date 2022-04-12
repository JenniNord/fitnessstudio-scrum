/**
 */
package sp.model.sp;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sprint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sp.model.sp.Sprint#getCommittedItem <em>Committed Item</em>}</li>
 * </ul>
 *
 * @see sp.model.sp.SPPackage#getSprint()
 * @model
 * @generated
 */
public interface Sprint extends EObject {
	/**
	 * Returns the value of the '<em><b>Committed Item</b></em>' reference list.
	 * The list contents are of type {@link sp.model.sp.WorkItem}.
	 * It is bidirectional and its opposite is '{@link sp.model.sp.WorkItem#getIsPlannedFor <em>Is Planned For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Committed Item</em>' reference list.
	 * @see sp.model.sp.SPPackage#getSprint_CommittedItem()
	 * @see sp.model.sp.WorkItem#getIsPlannedFor
	 * @model opposite="isPlannedFor" required="true"
	 * @generated
	 */
	EList<WorkItem> getCommittedItem();

} // Sprint
