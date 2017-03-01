<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:mw="http://www.mediawiki.org/xml/export-0.10/">
	<xsl:output method="text"/>

	<xsl:template match="text()" />

	<xsl:template match="/mw:mediawiki">
DROP TABLE IF EXISTS page;
DROP TABLE IF EXISTS user_;

CREATE TABLE user_(id int8 PRIMARY KEY, username varchar(255) NOT NULL);
CREATE TABLE page(id int8 PRIMARY KEY, title varchar(255) NOT NULL, content text NOT NULL, last_contributor_id int8 REFERENCES user_(id));
		<xsl:apply-templates />
DROP SEQUENCE IF EXISTS page_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE page_id_seq;
CREATE SEQUENCE user_id_seq;
SELECT setval('page_id_seq', max(id) + 1) FROM page;
SELECT setval('user_id_seq', max(id) + 1) FROM user_;
	</xsl:template>

	<xsl:template match="/mw:mediawiki/mw:page[mw:ns = '0']">
		<xsl:apply-templates />
INSERT INTO page(id, title, content, last_contributor_id) VALUES
(
	<xsl:value-of select="mw:id"/>,
	$MY_ESCAPE$<xsl:value-of select="mw:title"/>$MY_ESCAPE$,
	$MY_ESCAPE$<xsl:value-of select="mw:revision/mw:text"/>$MY_ESCAPE$,
	NULLIF($MY_ESCAPE$<xsl:value-of select="mw:revision/mw:contributor/mw:id"/>$MY_ESCAPE$, '')::bigint
);
	</xsl:template>

	<xsl:template match="/mw:mediawiki/mw:page/mw:revision/mw:contributor[.//mw:id]">
INSERT INTO user_(id, username) VALUES
(
	<xsl:value-of select="mw:id"/>,
	$MY_ESCAPE$<xsl:value-of select="mw:username"/>$MY_ESCAPE$
)
	ON CONFLICT DO NOTHING;
	</xsl:template>
</xsl:stylesheet>

