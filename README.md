# insertion-treemap

Insertion Treemap is a novel treemapping technique designed to deal with _dynamic hierarchies_.

To compile the code, from the root directory, run:

`mkdir bin; javac -d bin $(find . -name "*.java")`

To run the code that simply generates the rectangle series that can be used for analysis, run:

`java -cp ./bin com.eduardovernier.Main input_dir width height output_dir`

If you'd like to see and interact with the generated treemap, add the frag `-v`. To advance a revision, press `x`, and to go back, press `z`;

`java -cp ./bin com.eduardovernier.Main -v input_dir width height`

Some examples of datasets can be found [here](https://github.com/EduardoVernier/dynamic-map/tree/master/dataset).
