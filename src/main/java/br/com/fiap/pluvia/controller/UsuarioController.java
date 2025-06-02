package br.com.fiap.pluvia.controller;

import br.com.fiap.pluvia.dto.UsuarioDTO;
import br.com.fiap.pluvia.models.Usuario;
import br.com.fiap.pluvia.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@RequestBody UsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioService.registro(dto.getEmail(), dto.getSenha());
        return ResponseEntity.ok(usuario);
    }

}
