package ramo.klevis.test.bindingform;

import ramo.klevis.vaadinaddon.annotationcomponents.ArmatimeForm;

public class BeanController<T> {

	private ArmatimeForm<T> armatimeForm;
	private ArmatimeForm<Bean> armatimeForm2;

	public ArmatimeForm<Bean> createBindingForm(Bean bean, BeanView beanView) {

		armatimeForm2 = new ArmatimeForm<Bean>(Bean.class, beanView);
		armatimeForm2.setBean(bean);
		armatimeForm2.setImmediate(true);
		return armatimeForm2;
	}

	public ArmatimeForm<T> createBindingForm(Class bean, BeanView beanView) {

		armatimeForm = new ArmatimeForm<T>(bean, beanView);
		armatimeForm.setBeanToForm();
		armatimeForm.setImmediate(true);
		return armatimeForm;
	}

	public void clearForm() {
		if (armatimeForm != null) {
			armatimeForm.clearForm();
		}

		if (armatimeForm2 != null) {

			armatimeForm2.clearForm();
		}

	}

	public void fillForm(T bean) {
		if (armatimeForm != null) {
			armatimeForm.setBean(bean);
		}

	}

	public void fillForm(Bean bean) {

		if (armatimeForm2 != null) {

			armatimeForm2.setBean(bean);
		}
	}

}
