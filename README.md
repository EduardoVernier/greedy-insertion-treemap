# insertion-treemap

Insertion Treemap is a novel treemapping technique designed to deal with _dynamic hierarchies_.

To compile the code, from the root directory, run:

`mkdir bin; javac -d bin $(find . -name "*.java")`

To run the code that simply generates the rectangle series that can be used for analysis, run:

`java -cp ./bin com.eduardovernier.Main input_dir width height output_dir`

If you'd like to see and interact with the generated treemap, add the frag `-v`:

`java -cp ./bin com.eduardovernier.Main -v input_dir width height`
