package com.bht.banhuitong.client;

import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.widgets.menu.MenuItem;

public class IMenuItem extends MenuItem {


	public IMenuItem(String title, String keyTitle) {
		super();
		super.setTitle(title);
		super.setKeyTitle("Alt+" +keyTitle);
		initIMenuItem(keyTitle);
	}

	public void initIMenuItem(String keyTitle) {
		this.setKeys(getKeyIdentifier(keyTitle));
	}

	private KeyIdentifier getKeyIdentifier(String keyChar) {
		KeyIdentifier key = new KeyIdentifier();
		key.setKeyName(keyChar);
		key.setAltKey(true);
		return key;
	}
}
