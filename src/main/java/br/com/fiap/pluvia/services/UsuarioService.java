package br.com.fiap.pluvia.services;

import br.com.fiap.pluvia.dto.EnderecoDTO;
import br.com.fiap.pluvia.dto.UsuarioDTO;
import br.com.fiap.pluvia.models.Endereco;
import br.com.fiap.pluvia.models.Usuario;
import br.com.fiap.pluvia.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;

    public Usuario cadastrar(UsuarioDTO dto) {
        EnderecoDTO enderecoDTO = dto.getEndereco();

        Endereco endereco = enderecoService.buscarOuCriarPorCep(enderecoDTO.getCep());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setEndereco(endereco);

        return usuarioRepository.save(usuario);
    }

    //Login
    public Usuario registro(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        return usuario;
    }

    public Usuario buscarPeloEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não identificado."));
    }

    public Page<Usuario> listarPaginado(int pagina, int tamanho) {
        if (pagina < 0) {
            pagina = 0;
        }
        if (tamanho <= 0) {
            tamanho = 10;
        }

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("idUsuario").ascending());

        return usuarioRepository.findAll(pageable);
    }

    public void deletar(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."
                ));
        usuarioRepository.delete(usuario);
    }


}
