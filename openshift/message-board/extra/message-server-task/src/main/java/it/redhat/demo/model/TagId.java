package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class TagId {

	private String name;

	public TagId() {
	}

	public TagId(String name) {
		this.name = name;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Tag_id {
	 *   required string name = 1;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<TagId> {

		@Override
		public TagId readFrom(ProtoStreamReader reader) throws IOException {
			TagId tagId = new TagId();
			tagId.name = reader.readString( "name" );

			return tagId;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, TagId tagId) throws IOException {
			writer.writeString( "name", tagId.name );
		}

		@Override
		public Class<? extends TagId> getJavaClass() {
			return TagId.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Tag_id";
		}
	}
}
