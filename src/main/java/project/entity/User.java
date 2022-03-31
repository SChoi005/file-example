package project.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"사용자ID","이름","성","메일주소","전화번호","우편번호","주소"})
public class User implements Serializable{
    
    private static final long serialVersionUID = -1883999589975469540L;
    
    @JsonProperty("사용자ID") 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @JsonIgnore
    String password;
    
    @JsonProperty("이름")
    String firstName;
    
    @JsonProperty("성")
    String lastName;
    
    @JsonProperty("메일주소")
    String email;
    
    @JsonProperty("전화번호")
    String tel;
    
    @JsonProperty("우편번호")
    String zip;
    
    @JsonProperty("주소")
    String address;
    
}