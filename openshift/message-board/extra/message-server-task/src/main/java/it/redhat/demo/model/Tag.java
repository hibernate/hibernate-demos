package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class Tag {

	public static final String CACHE_NAME = Tag.class.getSimpleName();

	private String name;

	public Tag() {
	}

	public Tag(String name) {
		this.name = name;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Tag {
	 *   required string name = 1;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<Tag> {

		@Override
		public Tag readFrom(ProtoStreamReader reader) throws IOException {
			Tag tag = new Tag();
			tag.name = reader.readString( "name" );

			return tag;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, Tag tag) throws IOException {
			writer.writeString( "name", tag.name );
		}

		@Override
		public Class<? extends Tag> getJavaClass() {
			return Tag.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Tag";
		}
	}
}
