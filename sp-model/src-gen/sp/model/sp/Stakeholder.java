/**
 */
package sp.model.sp;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stakeholder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sp.model.sp.Stakeholder#getWorkitem <em>Workitem</em>}</li>
 * </ul>
 *
 * @see sp.model.sp.SPPackage#getStakeholder()
 * @model
 * @generated
 */
public interface Stakeholder extends EObject {
	/**
	 * Returns the value of the '<em><b>Workitem</b></em>' reference list.
	 * The list contents are of type {@link sp.model.sp.WorkItem}.
	 * It is bidirectional and its opposite is '{@link sp.model.sp.WorkItem#getStakeholder <em>Stakeholder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workitem</em>' reference list.
	 * @see sp.model.sp.SPPackage#getStakeholder_Workitem()
	 * @see sp.model.sp.WorkItem#getStakeholder
	 * @model opposite="stakeholder" required="true"
	 * @generated
	 */
	EList<WorkItem> getWorkitem();

} // Stakeholder
