package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminTest {

    @Test
    void criarAdminComNomeESenha() {
        Admin admin = new Admin();
        admin.setNome("Marfim");
        admin.setSenha("senha123");

        assertEquals("Marfim", admin.getNome());
        assertEquals("senha123", admin.getSenha());
        assertNotNull(admin.toString());
    }
}