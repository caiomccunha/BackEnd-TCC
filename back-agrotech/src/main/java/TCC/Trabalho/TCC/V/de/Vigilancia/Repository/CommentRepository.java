package TCC.Trabalho.TCC.V.de.Vigilancia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {}

