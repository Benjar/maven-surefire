package org.apache.maven.surefire;

/*
 * Copyright 2001-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.surefire.report.ForkingReport;
import org.codehaus.plexus.util.cli.StreamConsumer;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author Jason van Zyl
 * @version $Id$
 */
public class ForkingWriterStreamConsumer
    implements StreamConsumer
{
    private PrintWriter printWriter;

    private int standardPrefixLength;

    private int headingPrefixLength;

    boolean showHeading;

    public ForkingWriterStreamConsumer( Writer writer, boolean showHeading )
    {
        this.showHeading = showHeading;

        printWriter = new PrintWriter( writer );

        standardPrefixLength = ForkingReport.FORKING_PREFIX_STANDARD.length();

        headingPrefixLength = ForkingReport.FORKING_PREFIX_HEADING.length();
    }

    public void consumeLine( String line )
    {
        if ( line.startsWith( ForkingReport.FORKING_PREFIX_HEADING ) )
        {
            if ( showHeading )
            {
                printWriter.println( line.substring( headingPrefixLength ) );

                printWriter.flush();
            }
        }
        else if ( line.startsWith( ForkingReport.FORKING_PREFIX_STANDARD ) )
        {
            printWriter.println( line.substring( standardPrefixLength ) );

            printWriter.flush();
        }
        else
        {
            printWriter.println( line );

            printWriter.flush();
        }
    }
}