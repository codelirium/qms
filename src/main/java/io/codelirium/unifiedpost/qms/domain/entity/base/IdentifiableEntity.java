package io.codelirium.unifiedpost.qms.domain.entity.base;

import lombok.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;


@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class IdentifiableEntity<T extends Serializable> {

	public static final String FIELD_NAME_ID  = "id";
	public static final String COLUMN_NAME_ID = "ID";


	@Id
	@GeneratedValue(strategy = IDENTITY)
	private T id;

}
