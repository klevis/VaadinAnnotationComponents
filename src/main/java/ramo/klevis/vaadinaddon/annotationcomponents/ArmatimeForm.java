package ramo.klevis.vaadinaddon.annotationcomponents;

import org.vaadin.addon.formbinder.FormView;
import org.vaadin.addon.formbinder.PreCreatedFieldsHelper;
import org.vaadin.addon.formbinder.PropertyId;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

public class ArmatimeForm<T> extends Form {

	/**
	 * A customized Form implementation that uses pre created fields pattern by
	 * default. Fields for item properties are searched from component's or its
	 * layout's (java) fields.
	 * <p>
	 * Connection between field and property is made based on naming convention:
	 * e.g. property with id "weight" maps to field named "weightField". So for
	 * each propertyId that is wished to be editable in the form there should be
	 * appropriately named {@link Field} in either subclass of ViewBoundForm or
	 * in the layout/content used by it. E.g. a simple pojo like this:
	 * 
	 * <pre>
	 * <code>
	 * public class MyPojo {
	 * 		String foo;
	 * 		// getters, setters
	 * }
	 * </code>
	 * </pre>
	 * 
	 * can be connected to a custom form like this:
	 * 
	 * <pre>
	 * <code>
	 * public class MyPojoForm extends ViewBoundForm {
	 * 		
	 * 		// this will be connected to field "foo" in MyPojo
	 * 		public TextField fooField = new TextField();
	 * 
	 * 		public MyPojoForm {
	 * 			// Note, most commonly more complex layout 
	 * 			// or separate view class is used
	 * 			getLayout().addComponent(fooField);
	 * 		}
	 * 
	 * }
	 * </code>
	 * </pre>
	 * 
	 * <p>
	 * If the naming convention cannot be used for some reason, fields can be
	 * bound with {@link PropertyId} annotations. Also not that in this case the
	 * class containing fields must also be configured with {@link FormView}
	 * annotation.
	 * 
	 * @see PreCreatedFieldsHelper
	 * @see Form
	 */

	private Object[] customFieldSources;
	private ArmPreCreatedFieldsHelper preCreatedFieldsHelper;
	private Class<T> beanClass;
	Class<?> clazz;// per rekrusionin e clear
	ComponentContainer layout;
	T bean;
	Label errorLabel = new Label();

	private void clearBeanForm(Class<?> clazz) {

		java.lang.reflect.Field[] declaredFields = clazz.getDeclaredFields();
		for (java.lang.reflect.Field field : declaredFields) {

			field.setAccessible(true);
			String s = "" + field.getType();

			try {
				if (s.equalsIgnoreCase("int")) {
					field.set(bean, 0);
				} else if (s.equals("double")) {
					field.set(bean, 0.0);
				} else if (s.equals("float")) {
					field.set(bean, 0.0f);
				} else {

					field.set(bean, null);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void clearForm() {
		clazz = bean.getClass();
		while (clazz.getName().equals("java.lang.Object") == false) {

			clearBeanForm(clazz);
			clazz = (Class<T>) clazz.getSuperclass();
		}

		setBean(bean);
	}

	public void setBean(T bean) {
		this.bean = bean;

		BeanItem<T> newDataSource = new BeanItem<T>(bean);
		super.setItemDataSource(newDataSource);
	}

	public T getBean() {
		return bean;
	}

	public void configureErrorLabel() {

		this.errorLabel.setVisible(false);
		this.getFooter().addComponent(errorLabel);
		errorLabel.setContentMode(Label.CONTENT_XHTML);
	}

	public void setBeanToForm() {
		// TODO Auto-generated method stubb
		try {
			T newInstance = beanClass.newInstance();
			this.bean = newInstance;
			super.setItemDataSource(new BeanItem<T>(newInstance));

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ComponentContainer getContentLayput() {
		return layout;
	}

	public ArmatimeForm(Class<T> beanClass) {
		this.beanClass = beanClass;
		configureErrorLabel();
		ArmPreCreatedFieldsHelper fieldFactory = new ArmPreCreatedFieldsHelper(
				beanClass, this);

		this.setPreCreatedFieldsHelper(fieldFactory);
		setFormFieldFactory(fieldFactory);
		// this.setBeanToForm();
	}

	public ArmatimeForm(Class<T> beanClass, ComponentContainer layout) {

		this.beanClass = beanClass;
		configureErrorLabel();
		this.layout = layout;
		setContent(layout);
		// this.setBeanToForm();
	}

	@Override
	public void commit() throws SourceException, InvalidValueException {
		// TODO Auto-generated method stub
		try {
			super.commit();

		} catch (InvalidValueException e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	// @Override
	// public void validate() throws InvalidValueException {
	//
	// // TODO Auto-generated method stub
	// errorLabel.setVisible(false);
	// try {
	// super.validate();
	//
	// } catch (InvalidValueException e) {
	// // TODO: handle exception
	// errorLabel.setVisible(true);
	//
	// errorLabel.addStyleName("v-form-errormessage");
	// errorLabel.setValue("</p>" + "<b> <font color=\"red\">"
	// + e.getMessage() + "</font></b>");
	//
	// }
	// }

	/**
	 * Sets the layout (aka view) of this form. The layout is used as a primary
	 * source for pre created fields, the actual form as a secondary source.
	 * 
	 * @param layout
	 */
	public void setContent(ComponentContainer layout) {

		if (layout instanceof Layout) {
			super.setLayout((Layout) layout);
		} else {
			// form only accepts Layout as content so wrap into CssLayout if
			// necessary (e.g. CustomComponent)
			CssLayout cssLayout = new CssLayout();
			cssLayout.addComponent(layout);
			super.setLayout(cssLayout);
		}
		ArmPreCreatedFieldsHelper fieldFactory = new ArmPreCreatedFieldsHelper(
				beanClass, layout, this, customFieldSources);
		this.setPreCreatedFieldsHelper(fieldFactory);
		setFormFieldFactory(fieldFactory);
	}

	/**
	 * @param fieldSource
	 *            custom source object from which fields are seeked. Layout and
	 *            the form itself are automatically seeked.
	 */
	public void setCustomFieldSources(Object... fieldSource) {
		this.customFieldSources = fieldSource;
		if (getFormFieldFactory() instanceof ArmPreCreatedFieldsHelper) {
			ArmPreCreatedFieldsHelper fieldFactory = new ArmPreCreatedFieldsHelper(
					beanClass, getLayout(), this, customFieldSources);
			this.setPreCreatedFieldsHelper(fieldFactory);
			setFormFieldFactory(fieldFactory);
		}
	}

	public Object[] getCustomFieldSouces() {
		return customFieldSources;
	}

	@Override
	public void setLayout(Layout newLayout) {
		if (newLayout == null) {
			super.setLayout(newLayout);
		} else {
			setContent(newLayout);
		}
	}

	@Override
	protected void attachField(Object propertyId, Field field) {
		// NOP as fields are expected to be attached via view

	}

	@Override
	protected void detachField(Field field) {
		// NOP as fields are expected to be attached via view
	}

	public void setPreCreatedFieldsHelper(
			ArmPreCreatedFieldsHelper preCreatedFieldsHelper) {
		this.preCreatedFieldsHelper = preCreatedFieldsHelper;
	}

	public ArmPreCreatedFieldsHelper getPreCreatedFieldsHelper() {
		return preCreatedFieldsHelper;
	}

}
