package io.codelirium.unifiedpost.qms.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class RESTResponseBody implements Serializable {

	private static final long serialVersionUID = -5640756286027710938L;


	private String status;

	private String type;

}
