/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ramo.klevis.main;

import java.util.ArrayList;

import ramo.klevis.test.annotationtable.BeanTable;
import ramo.klevis.test.annotationtable.Document;
import ramo.klevis.test.bindingform.Bean;
import ramo.klevis.test.bindingform.BeanController;
import ramo.klevis.test.bindingform.BeanView;
import ramo.klevis.vaadinaddon.annotationcomponents.AnnotationTable;
import ramo.klevis.vaadinaddon.annotationcomponents.ArmatimeForm;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends Application {
	private Window window;

	@Override
	public void init() {
		window = new Window("My Vaadin Application");
		setMainWindow(window);

		// binding form example
		BeanController<Bean> beanController = new BeanController<Bean>();

		final ArmatimeForm<Bean> bindingFormWithvalidation = beanController
				.createBindingForm(new Bean(), new BeanView());

		window.addComponent(bindingFormWithvalidation);
		// end of binding form example

		Button button = new Button("Show me some values of form");
		button.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				bindingFormWithvalidation.commit();
				System.out.println("Values of your form are "
						+ bindingFormWithvalidation.getBean());

			}
		});

		Button button2 = new Button("Clear all from form");
		button2.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				bindingFormWithvalidation.clearForm();

				System.out.println("Form cleaned "
						+ bindingFormWithvalidation.getBean());
			}
		});
		window.addComponent(button);
		window.addComponent(button2);

		// annotation table example
		final AnnotationTable<BeanTable> annotationTable = new AnnotationTable<BeanTable>(
				BeanTable.class);

		window.addComponent(annotationTable);
		// end of annotation table example

		Button button3 = new Button("Add some values to table");

		button3.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				ArrayList<BeanTable> arrayList = new ArrayList<BeanTable>();

				BeanTable beanTable = new BeanTable();
				beanTable.setAge(8);
				Document document = new Document();
				document.setId("56-iso 9");
				document.setContent("Example of table");
				beanTable.setDocument(document);
				beanTable.setFather("Kali");
				beanTable.setName("Demi");
				beanTable.setSurname("Dragoi ");
				arrayList.add(beanTable);

				annotationTable.addToContainer(arrayList);

			}
		});

		Button button4 = new Button("Clear the table");

		button4.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				annotationTable.clearContainer();
			}
		});

		window.addComponent(button3);
		window.addComponent(button4);
	}

}
