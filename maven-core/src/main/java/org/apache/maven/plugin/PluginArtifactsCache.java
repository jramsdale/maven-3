package org.apache.maven.plugin;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.project.MavenProject;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * Caches plugin artifacts. <strong>Warning:</strong> This is an internal utility interface that is only public for
 * technical reasons, it is not part of the public API. In particular, this interface can be changed or deleted without
 * prior notice.
 * 
 * @author Igor Fedorenko
 * @author Benjamin Bentmann
 */
public interface PluginArtifactsCache
{

    /**
     * A cache key.
     */
    interface Key
    {
        // marker interface for cache keys
    }

    public static class CacheRecord
    {

        public final List<Artifact> artifacts;

        public final PluginResolutionException exception; 

        public CacheRecord( List<Artifact> artifacts )
        {
            this.artifacts = artifacts;
            this.exception = null;
        }

        public CacheRecord( PluginResolutionException exception )
        {
            this.artifacts = null;
            this.exception = exception;
        }
    }

    Key createKey( Plugin plugin, DependencyFilter extensionFilter, List<RemoteRepository> repositories,
                   RepositorySystemSession session );

    CacheRecord get( Key key ) throws PluginResolutionException;

    CacheRecord put( Key key, List<Artifact> pluginArtifacts );

    CacheRecord put( Key key, PluginResolutionException e );

    void flush();

    /**
     * Registers the specified cache record for usage with the given project. Integrators can use the information
     * collected from this method in combination with a custom cache implementation to dispose unused records from the
     * cache.
     * 
     * @param project The project that employs the plugin realm, must not be {@code null}.
     * @param record The cache record being used for the project, must not be {@code null}.
     */
    void register( MavenProject project, CacheRecord record );

}
