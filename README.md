# CSIRO-Project-

2. Product Vision Statement

The Medical Research Platform (MRP) is for CSIRO’s E-Health Research division, who needs this product to assist the field of medical research. The platform is a web application that will provide a way to import, organise, and analyse medical research data (specifically, clinical research data). Unlike more traditional ways of analysing clinical research data (eg. Excel spreadsheets), our product leverages modern standards specifically designed for the easy collation and analysis of research data, allowing a greater level of insight and perspective to be gained from analysing existing datasets.

3. Product Vision
3.1 Customers and benefits

The client organisation, to us, is:
Made up of research-driven and data-dependent scientists
Largely, a group of advanced technical users, capable of handling above-average complexity software
Interested in improving their research focus through the use of a specialised database-query interaction tool
Our system will:
Effectively share and contrast data from clinical studies
Unify terminology and units of measure used in data recordings
Enable cross-domain and cross-study exploration of data
Enrich data, preventing data loss during format transformation

3.2 Key factors used to judge quality

Highly important
Functionality and correctness (far and away the most critical point)
Extensibility and flexibility (client not sure yet what complete project will entail)
Internal efficiency (important due to database-style querying and slice methodology)
Maintainability (project team chose to use java for this reason, as the client org is most familiar in that language and would struggle to maintain and improve software internally otherwise)
Usability (important alongside functionality, although highly technical clients shouldn’t have trouble)
Moderately important (desirable, but with some wiggle room)
Security (at this stage, project is wholly internal to client org, but does handle (non-identifying) patient information and so should be at least moderately secure)
Testability
Reliability
Not important
Low-level performance (detailed code optimisation and memory management etc are not as vital relative to overall functionality)
Portability (no real need at this stage)

3.3 Key features and technology

The system will take raw data in various different formats (xml, csv, etc) and transform it into an RDF formatted Data Cube. The ability to take a discrete interval along an axis (a ‘slice’) of said cube is the impetus for the development, as this allows for easy correlation of multiple variables.
Furthermore, functionality allowing for interrogation of the cube from languages and platforms will be a stretch goal of the project.

3.4 Crucial product factors

Interfacing with other modelling languages/technologies (Eg. R)
Dynamic Data Cube slicing (core is static slicing)
JVM compatibility
Must not expose secure medical records or private data - the system cannot introduce security flaws
Must not damage or alter static data (original experimental records, for instance), rather create new copies
Queries must be idempotent
Users should be able to use the system without highly specific domain knowledge
The system should exist as a package that does not require extensive effort to deploy (multiple researchers may use many different instances, thus this req.)
The system must be documented to the extent that a reasonably competent user does not require developer assistance in order to use it
