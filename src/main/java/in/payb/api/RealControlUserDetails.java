package in.payb.api;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class RealControlUserDetails implements UserDetails {

		private static final long serialVersionUID = 7670342262220099448L;
		private String company;
		private String username;
		private String password;
		private String sharedsecret;
		private String refundpassword;
		private String key;
		
		public RealControlUserDetails(String company, String username, String password) {
			this.company = company;
			this.username = (username != null ? username : "admin");
			this.password = password;
			
		}
		
		public void scrubSensitiveDetails() {
			this.password = null;
			this.sharedsecret = null;
			this.refundpassword = null;
		}
		
		public String getKey() {
			return key;
		}



		public void setKey(String key) {
			this.key = key;
		}



		public String getSharedsecret() {
			return sharedsecret;
		}



		public void setSharedsecret(String sharedsecret) {
			this.sharedsecret = sharedsecret;
		}



		public String getRefundpassword() {
			return refundpassword;
		}



		public void setRefundpassword(String refundpassword) {
			this.refundpassword = refundpassword;
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
