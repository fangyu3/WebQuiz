package com.fangyu3.webquiz.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user")
public class User implements UserDetails{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Email
    @Pattern(regexp=".+@.+\\..+")
	@Column(name="email")
	private String email;
	
	@NotNull
	@Size(min = 5)
	@Column(name="password")
	private String password;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private List<Quiz> quizzes = new ArrayList<>();
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
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
