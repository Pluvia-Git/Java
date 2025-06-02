package br.com.fiap.pluvia.repositories;

import br.com.fiap.pluvia.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Optional<Endereco> findByCep(String cep);
}
