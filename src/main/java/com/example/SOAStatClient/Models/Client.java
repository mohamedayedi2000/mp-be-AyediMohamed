package com.example.SOAStatClient.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String pname;
    private String address;
    private String email;
    private String tel;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Facture> factures;

    @Column(name = "statu_client")
    private Boolean statuClient;

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", this.id);
        json.put("code", this.code);
        json.put("name", this.name);
        json.put("pname", this.pname);
        json.put("email", this.email);
        json.put("tel", this.tel);
        json.put("address",this.address);


        json.put("statuClient", this.statuClient);
        return json;
    }

    public List<Facture> getFactures() {
        return factures;
    }
    public Long getId() {
        return id;
    }
    public Boolean getStatuClient() {
        return statuClient;
    }
}
