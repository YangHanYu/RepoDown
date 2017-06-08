package com.ky.repodown;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class VersionService {
	public void conn(){
	}
	
	public static void main(String[] args) {
		ArtifactVersion artifactVersion = new DefaultArtifactVersion("release-2.1");
		System.out.println(artifactVersion.getMajorVersion());
	}
	
}
