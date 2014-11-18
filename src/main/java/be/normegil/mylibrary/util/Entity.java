package be.normegil.mylibrary.util;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.UUID;

@MappedSuperclass
@Access(AccessType.FIELD)
public class Entity {

	@Id
	@Column(name = "ID")
	@XmlAttribute
	private UUID id;

	public UUID getId() {
		return id;
	}

	public Entity() {
		this.id = UUID.randomUUID();
	}
}
