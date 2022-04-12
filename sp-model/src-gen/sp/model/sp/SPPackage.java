/**
 */
package sp.model.sp;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see sp.model.sp.SPFactory
 * @model kind="package"
 * @generated
 */
public interface SPPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://sp.model/sp";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SPPackage eINSTANCE = sp.model.sp.impl.SPPackageImpl.init();

	/**
	 * The meta object id for the '{@link sp.model.sp.impl.StakeholderImpl <em>Stakeholder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sp.model.sp.impl.StakeholderImpl
	 * @see sp.model.sp.impl.SPPackageImpl#getStakeholder()
	 * @generated
	 */
	int STAKEHOLDER = 0;

	/**
	 * The feature id for the '<em><b>Workitem</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STAKEHOLDER__WORKITEM = 0;

	/**
	 * The number of structural features of the '<em>Stakeholder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STAKEHOLDER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Stakeholder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STAKEHOLDER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link sp.model.sp.impl.BacklogImpl <em>Backlog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sp.model.sp.impl.BacklogImpl
	 * @see sp.model.sp.impl.SPPackageImpl#getBacklog()
	 * @generated
	 */
	int BACKLOG = 1;

	/**
	 * The feature id for the '<em><b>Workitems</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKLOG__WORKITEMS = 0;

	/**
	 * The number of structural features of the '<em>Backlog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKLOG_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Backlog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKLOG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link sp.model.sp.impl.WorkItemImpl <em>Work Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sp.model.sp.impl.WorkItemImpl
	 * @see sp.model.sp.impl.SPPackageImpl#getWorkItem()
	 * @generated
	 */
	int WORK_ITEM = 2;

	/**
	 * The feature id for the '<em><b>Is Planned For</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORK_ITEM__IS_PLANNED_FOR = 0;

	/**
	 * The feature id for the '<em><b>Importance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORK_ITEM__IMPORTANCE = 1;

	/**
	 * The feature id for the '<em><b>Effort</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORK_ITEM__EFFORT = 2;

	/**
	 * The feature id for the '<em><b>Stakeholder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORK_ITEM__STAKEHOLDER = 3;

	/**
	 * The number of structural features of the '<em>Work Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORK_ITEM_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Work Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORK_ITEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link sp.model.sp.impl.SprintImpl <em>Sprint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sp.model.sp.impl.SprintImpl
	 * @see sp.model.sp.impl.SPPackageImpl#getSprint()
	 * @generated
	 */
	int SPRINT = 3;

	/**
	 * The feature id for the '<em><b>Committed Item</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPRINT__COMMITTED_ITEM = 0;

	/**
	 * The number of structural features of the '<em>Sprint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPRINT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Sprint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPRINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link sp.model.sp.impl.PlanImpl <em>Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sp.model.sp.impl.PlanImpl
	 * @see sp.model.sp.impl.SPPackageImpl#getPlan()
	 * @generated
	 */
	int PLAN = 4;

	/**
	 * The feature id for the '<em><b>Stakeholders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__STAKEHOLDERS = 0;

	/**
	 * The feature id for the '<em><b>Backlog</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__BACKLOG = 1;

	/**
	 * The feature id for the '<em><b>Sprints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__SPRINTS = 2;

	/**
	 * The number of structural features of the '<em>Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link sp.model.sp.Stakeholder <em>Stakeholder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stakeholder</em>'.
	 * @see sp.model.sp.Stakeholder
	 * @generated
	 */
	EClass getStakeholder();

	/**
	 * Returns the meta object for the reference list '{@link sp.model.sp.Stakeholder#getWorkitem <em>Workitem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Workitem</em>'.
	 * @see sp.model.sp.Stakeholder#getWorkitem()
	 * @see #getStakeholder()
	 * @generated
	 */
	EReference getStakeholder_Workitem();

	/**
	 * Returns the meta object for class '{@link sp.model.sp.Backlog <em>Backlog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Backlog</em>'.
	 * @see sp.model.sp.Backlog
	 * @generated
	 */
	EClass getBacklog();

	/**
	 * Returns the meta object for the containment reference list '{@link sp.model.sp.Backlog#getWorkitems <em>Workitems</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Workitems</em>'.
	 * @see sp.model.sp.Backlog#getWorkitems()
	 * @see #getBacklog()
	 * @generated
	 */
	EReference getBacklog_Workitems();

	/**
	 * Returns the meta object for class '{@link sp.model.sp.WorkItem <em>Work Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Work Item</em>'.
	 * @see sp.model.sp.WorkItem
	 * @generated
	 */
	EClass getWorkItem();

	/**
	 * Returns the meta object for the reference '{@link sp.model.sp.WorkItem#getIsPlannedFor <em>Is Planned For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Is Planned For</em>'.
	 * @see sp.model.sp.WorkItem#getIsPlannedFor()
	 * @see #getWorkItem()
	 * @generated
	 */
	EReference getWorkItem_IsPlannedFor();

	/**
	 * Returns the meta object for the attribute '{@link sp.model.sp.WorkItem#getImportance <em>Importance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Importance</em>'.
	 * @see sp.model.sp.WorkItem#getImportance()
	 * @see #getWorkItem()
	 * @generated
	 */
	EAttribute getWorkItem_Importance();

	/**
	 * Returns the meta object for the attribute '{@link sp.model.sp.WorkItem#getEffort <em>Effort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Effort</em>'.
	 * @see sp.model.sp.WorkItem#getEffort()
	 * @see #getWorkItem()
	 * @generated
	 */
	EAttribute getWorkItem_Effort();

	/**
	 * Returns the meta object for the reference '{@link sp.model.sp.WorkItem#getStakeholder <em>Stakeholder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Stakeholder</em>'.
	 * @see sp.model.sp.WorkItem#getStakeholder()
	 * @see #getWorkItem()
	 * @generated
	 */
	EReference getWorkItem_Stakeholder();

	/**
	 * Returns the meta object for class '{@link sp.model.sp.Sprint <em>Sprint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sprint</em>'.
	 * @see sp.model.sp.Sprint
	 * @generated
	 */
	EClass getSprint();

	/**
	 * Returns the meta object for the reference list '{@link sp.model.sp.Sprint#getCommittedItem <em>Committed Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Committed Item</em>'.
	 * @see sp.model.sp.Sprint#getCommittedItem()
	 * @see #getSprint()
	 * @generated
	 */
	EReference getSprint_CommittedItem();

	/**
	 * Returns the meta object for class '{@link sp.model.sp.Plan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan</em>'.
	 * @see sp.model.sp.Plan
	 * @generated
	 */
	EClass getPlan();

	/**
	 * Returns the meta object for the containment reference list '{@link sp.model.sp.Plan#getStakeholders <em>Stakeholders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Stakeholders</em>'.
	 * @see sp.model.sp.Plan#getStakeholders()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Stakeholders();

	/**
	 * Returns the meta object for the containment reference '{@link sp.model.sp.Plan#getBacklog <em>Backlog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Backlog</em>'.
	 * @see sp.model.sp.Plan#getBacklog()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Backlog();

	/**
	 * Returns the meta object for the containment reference list '{@link sp.model.sp.Plan#getSprints <em>Sprints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sprints</em>'.
	 * @see sp.model.sp.Plan#getSprints()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Sprints();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SPFactory getSPFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link sp.model.sp.impl.StakeholderImpl <em>Stakeholder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sp.model.sp.impl.StakeholderImpl
		 * @see sp.model.sp.impl.SPPackageImpl#getStakeholder()
		 * @generated
		 */
		EClass STAKEHOLDER = eINSTANCE.getStakeholder();

		/**
		 * The meta object literal for the '<em><b>Workitem</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STAKEHOLDER__WORKITEM = eINSTANCE.getStakeholder_Workitem();

		/**
		 * The meta object literal for the '{@link sp.model.sp.impl.BacklogImpl <em>Backlog</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sp.model.sp.impl.BacklogImpl
		 * @see sp.model.sp.impl.SPPackageImpl#getBacklog()
		 * @generated
		 */
		EClass BACKLOG = eINSTANCE.getBacklog();

		/**
		 * The meta object literal for the '<em><b>Workitems</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BACKLOG__WORKITEMS = eINSTANCE.getBacklog_Workitems();

		/**
		 * The meta object literal for the '{@link sp.model.sp.impl.WorkItemImpl <em>Work Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sp.model.sp.impl.WorkItemImpl
		 * @see sp.model.sp.impl.SPPackageImpl#getWorkItem()
		 * @generated
		 */
		EClass WORK_ITEM = eINSTANCE.getWorkItem();

		/**
		 * The meta object literal for the '<em><b>Is Planned For</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORK_ITEM__IS_PLANNED_FOR = eINSTANCE.getWorkItem_IsPlannedFor();

		/**
		 * The meta object literal for the '<em><b>Importance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WORK_ITEM__IMPORTANCE = eINSTANCE.getWorkItem_Importance();

		/**
		 * The meta object literal for the '<em><b>Effort</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WORK_ITEM__EFFORT = eINSTANCE.getWorkItem_Effort();

		/**
		 * The meta object literal for the '<em><b>Stakeholder</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORK_ITEM__STAKEHOLDER = eINSTANCE.getWorkItem_Stakeholder();

		/**
		 * The meta object literal for the '{@link sp.model.sp.impl.SprintImpl <em>Sprint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sp.model.sp.impl.SprintImpl
		 * @see sp.model.sp.impl.SPPackageImpl#getSprint()
		 * @generated
		 */
		EClass SPRINT = eINSTANCE.getSprint();

		/**
		 * The meta object literal for the '<em><b>Committed Item</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPRINT__COMMITTED_ITEM = eINSTANCE.getSprint_CommittedItem();

		/**
		 * The meta object literal for the '{@link sp.model.sp.impl.PlanImpl <em>Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sp.model.sp.impl.PlanImpl
		 * @see sp.model.sp.impl.SPPackageImpl#getPlan()
		 * @generated
		 */
		EClass PLAN = eINSTANCE.getPlan();

		/**
		 * The meta object literal for the '<em><b>Stakeholders</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__STAKEHOLDERS = eINSTANCE.getPlan_Stakeholders();

		/**
		 * The meta object literal for the '<em><b>Backlog</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__BACKLOG = eINSTANCE.getPlan_Backlog();

		/**
		 * The meta object literal for the '<em><b>Sprints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__SPRINTS = eINSTANCE.getPlan_Sprints();

	}

} //SPPackage
