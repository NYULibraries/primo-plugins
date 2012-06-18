Primo Enrichment Plugins
========================
Primo Enrichment Plugins are hooks into the Primo normalization process that allow PNX records
to be modified.
> Just before the PNX records are stored in the database, Primo allows you to enrich the PNX 
records via the enrichment set assigned to the pipe. 
These sets include a series of modifications that Primo applies to each PNX record. 
The modifications may include one or more system-defined enrichments and/or a single enrichment 
plugin that you may have created using the old EnrichmentPlugin interface. [1]

NYU uses the following Primo Enrichment Plugins

1.  BSNToOCLCMapper: Includes OCLC Numbers in the PNX record.  
    Uses a mapping table in the NYU Data Warehouse to map from Aleph BSNs to OCLC numbers.

[1] [EL Commons](http://exlibrisgroup.org/display/PrimoOI/Enrichment+Plug-In+%28new+version%29, "Enrichment Plug-In (new version)")