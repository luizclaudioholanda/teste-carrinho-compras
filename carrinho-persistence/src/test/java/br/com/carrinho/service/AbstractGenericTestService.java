package br.com.carrinho.service;

import java.io.Serializable;

import org.junit.Test;

public abstract class AbstractGenericTestService<T, ID extends Serializable> extends AbstractGenericBaseTestService<T, ID> implements InterfaceTestCRUD{

	@Override
	@Test
	public void mustSaveAnObjectCorrectly() {
		super.saveSuccess();
	}

	@Override
	@Test
	public void mustDeleteAnObjectCorrectly() {
		super.deleteSuccess();
	}

	@Override
	@Test
	public void mustUpdateAnObjectCorretcly() {
		super.updateSuccess();
	}

	@Override
	@Test
	public void mustReturnAllCorrectly() {
		super.allObjectsSuccessfully();
	}

	@Override
	@Test
	public void mustFindByIdCorrectly() {
		super.findByIdSuccess();
	}
}
