package in.payb.api;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class RealControlUserDetails implements UserDetails {

		private static final long serialVersionUID = 7670342262220099448L;
		private String company;
		private String username;
		private String password;
		
		public RealControlUserDetails(String company, String username, String password) {
			this.company = company;
			this.username = (username != null ? username : "admin");
			this.password = password;
			
		}
		
		public String toString() {
			return this.company + "!" + this.username;
		}
		
		@Override
		public Collection<GrantedAuthority> getAuthorities() {
			return null;
		}
		
		public String getCompany() {
			return this.company;
		}
		
		@Override
		public String getUsername() {
			return this.username;
		}
		
		@Override
		public String getPassword() {
			return this.password;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
}
