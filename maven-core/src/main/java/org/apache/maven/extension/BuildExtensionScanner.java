package org.apache.maven.extension;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.profiles.ProfileManager;

import java.io.File;
import java.util.Map;

public interface BuildExtensionScanner
{
    
    String ROLE = BuildExtensionScanner.class.getName();
    
    void scanForBuildExtensions( File pom, ArtifactRepository localRepository, ProfileManager globalProfileManager, Map pomFilesById )
        throws ExtensionScanningException;

}