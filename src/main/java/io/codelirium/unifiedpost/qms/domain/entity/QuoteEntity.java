package io.codelirium.unifiedpost.qms.domain.entity;

import io.codelirium.unifiedpost.qms.domain.entity.base.IdentifiableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import static io.codelirium.unifiedpost.qms.domain.entity.base.IdentifiableEntity.COLUMN_NAME_ID;
import static io.codelirium.unifiedpost.qms.domain.entity.base.IdentifiableEntity.FIELD_NAME_ID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = QuoteEntity.TABLE_NAME)
@AttributeOverride(name = FIELD_NAME_ID, column = @Column(name = COLUMN_NAME_ID))
public class QuoteEntity extends IdentifiableEntity<Long> implements Serializable {

	private static final long serialVersionUID = -828276729279891177L;


	static final String TABLE_NAME = "QUOTES";


	@Column(name = "TEXT", nullable = false)
	private String text;

	@Column(name = "AUTHOR", nullable = true)
	private String author;

}
