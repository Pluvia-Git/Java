package br.com.fiap.pluvia.controller;

import br.com.fiap.pluvia.config.JwtUtil;
import br.com.fiap.pluvia.dto.IntroDTO;
import br.com.fiap.pluvia.dto.UsuarioDTO;
import br.com.fiap.pluvia.models.Usuario;
import br.com.fiap.pluvia.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<EntityModel<IntroDTO>> intro() {
        IntroDTO dto = new IntroDTO("Área administrativa de usuários.");

        EntityModel<IntroDTO> resource = EntityModel.of(dto);

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findByEmail(null))
                .withRel("encontrar--usuário-por-email"));

//        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios())
//                .withRel("paginação-usuários"));

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).cadastrar(null))
                .withRel("cadastrar-usuário"));

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).registro(null))
                .withRel("login-de-usuário"));

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).deletar(null))
                .withRel("deletar-usuário"));

        return ResponseEntity.ok(resource);

    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarPeloEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@RequestBody @Valid UsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody @Valid UsuarioDTO dto) {
        Usuario usuario = usuarioService.registro(dto.getEmail(), dto.getSenha());
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/paginada")
    public ResponseEntity<Page<Usuario>> listarUsuarios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        return ResponseEntity.ok(usuarioService.listarPaginado(pagina, tamanho));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deletar(@PathVariable String email) {
        usuarioService.deletar(email);
        return ResponseEntity.ok("Usuário deletado com sucesso.");
    }

}
