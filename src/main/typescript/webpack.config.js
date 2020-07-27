const path = require('path');

module.exports = {
  entry: {
    main: './src/index.ts',
    datepicker: './src/datepicker.ts',
  },
  devtool: 'source-map',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      }, {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      }
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
  output: {
    filename: '[name]-bundle.js',
    path: path.resolve(__dirname, '../resources/META-INF/resources'),
  }
};
