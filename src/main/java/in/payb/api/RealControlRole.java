package in.payb.api;

import org.springframework.security.core.GrantedAuthority;

public enum RealControlRole implements GrantedAuthority {
	ADMIN(0), USER(1);

	private int bit;

	RealControlRole(int bit) {
		this.bit = bit;
	}

	public String getAuthority() {
		return toString();
	}
}
