package ramo.klevis.vaadinaddon.annotationcomponents;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ramo.klevis.vaadinaddon.annotations.ATable;
import ramo.klevis.vaadinaddon.annotations.ToTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Runo;

public class AnnotationTable<T> extends Table {

	Class<T> beanClass;
	private List<T> container = new ArrayList<T>();
	private BeanItemContainer<T> newDataSource;

	public AnnotationTable(Class<T> beanClass) {
		// TODO Auto-generated constructor stub
		this.beanClass = beanClass;
		this.setStyleName(Runo.TABLE_SMALL);
		setBeanToBind();

	}

	boolean firstTime = false;

	List<String> columnsAnnotation = new ArrayList<String>();
	List<String> columnHeaders = new ArrayList<String>();

	private void setBeanToBind() {

		if (firstTime == false) {
			newDataSource = new BeanItemContainer<T>(beanClass, container);
			this.setContainerDataSource(newDataSource);
			configureATableAnnotaion();
			firstTime = true;
		}

		Field[] declaredFields = beanClass.getDeclaredFields();

		configureToTableAnnotaion(declaredFields, columnsAnnotation,
				columnHeaders);
		if (beanClass.getSuperclass().getName().equals("java.lang.Object") == false) {
			beanClass = (Class<T>) beanClass.getSuperclass();
			setBeanToBind();
		}

		String[] columnHeaderArray = new String[columnHeaders.size()];
		int index = 0;
		for (String string : columnHeaders) {
			columnHeaderArray[index] = string;
			index++;

		}

		this.setVisibleColumns(columnsAnnotation.toArray());
		this.setColumnHeaders(columnHeaderArray);

	}

	/**
	 * 
	 * @param declaredFields
	 * @param columnsAnnotation
	 * @param columnHeaders
	 * @return
	 * 
	 * 
	 *         Kjo metode mere parametrat e annotaion ToTable dhe i vendos
	 *         tabeles
	 * 
	 */
	private String[] configureToTableAnnotaion(Field[] declaredFields,
			List<String> columnsAnnotation, List<String> columnHeaders) {
		for (Field field : declaredFields) {

			field.setAccessible(true);
			Annotation[] annotations = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {

				if (annotation instanceof ToTable) {

					ToTable toTable = (ToTable) annotation;
					columnHeaders.add(toTable.columnName());
					if (toTable.nestedvalue().isEmpty())
						columnsAnnotation.add(field.getName());
					else {
						newDataSource.addNestedContainerProperty(toTable
								.nestedvalue());
						columnsAnnotation.add(toTable.nestedvalue());
					}

				}
			}
		}

		String[] columnHeaderArray = new String[columnHeaders.size()];

		return columnHeaderArray;
	}

	/**
	 * Kjo metode mere parametrat e annotaion ATable dhe i vendos tabeles Nqs
	 * duhet shtuar ndonje parameter tjeter thjesht shtohet te annotation ATable
	 * dhe gjithashtu shtohet
	 * 
	 * nje IF te llogjika
	 */
	private void configureATableAnnotaion() {
		Annotation[] annotations2 = beanClass.getAnnotations();
		for (Annotation annotation : annotations2) {

			if (annotation instanceof ATable) {

				ATable aTable = (ATable) annotation;

				// eshte pa if se ne te dy rastet ka nje vlere qe ka kuptim
				this.setPageLength(aTable.pageLength());
				this.setImmediate(aTable.imetdiate());
				this.setSelectable(aTable.selected());
				this.setMultiSelect(aTable.multiSelect());
				if (!aTable.caption().isEmpty()) {
					this.setCaption(aTable.caption());
				}

				if (!aTable.width().isEmpty()) {
					this.setWidth(aTable.width());
				}
				if (!aTable.height().isEmpty()) {
					this.setHeight(aTable.height());
				}

			}
		}
	}

	public boolean isEmty() {
		return container.isEmpty();

	}

	public List<T> getBeans() {

		return container;
	}

	public void addToContainer(T bean) {

		container.add(bean);
		newDataSource.addAll(container);

	}

	public void addToContainer(List<T> listBean) {
		container.addAll(listBean);
		newDataSource.addAll(container);
	}

	public void clearContainer() {

		container.clear();
		newDataSource.removeAllItems();

	}

	public void removeFromContainer(T t) {

		boolean remove = container.remove(t);
		newDataSource.removeItem(t);

	}

	public T getSelected() {
		if (this.isSelectable() == false) {
			this.setSelectable(true);
		}

		Object value2 = this.getValue();

		System.out.println("Selected   " + value2);
		return (T) value2;
	}

	public void removeSelected() {

		removeFromContainer(getSelected());

	}

	public void removeMultiSelected(T t[]) {

		for (T t2 : t) {

			removeFromContainer(t2);
		}
	}
}
